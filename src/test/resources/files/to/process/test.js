"use strict";

/**
 * Code borrowed from: https://github.com/omniscientjs/omniscient
 * Praise functional programming for UIs
 */

var filter = require('lodash.pick'),
    isEqual = require('lodash.isequal');


/**
 * Directly fetch `shouldComponentUpdate` mixin to use outside of Omniscient.
 * You can do this if you don't want to use Omniscients syntactic sugar.
 *
 * @param {Object} nextProps Next props. Can be objects of cursors, values or immutable structures
 * @param {Object} nextState Next state. Can be objects of values or immutable structures
 *
 * @property {Function} isCursor Get default isCursor
 * @property {Function} isEqualState Get default isEqualState
 * @property {Function} isEqualProps Get default isEqualProps
 * @property {Function} isEqualCursor Get default isEqualCursor
 * @property {Function} isEqualImmutable Get default isEqualImmutable
 * @property {Function} isImmutable Get default isImmutable
 * @property {Function} isIgnorable Get default isIgnorable
 * @property {Function} debug Get default debug
 *
 * @module shouldComponentUpdate
 * @returns {Component}
 * @api public
 */
module.exports = factory();

/**
 * Create a “local” instance of the shouldComponentUpdate with overriden defaults.
 *
 * ### Options
 * ```js
 * {
 *   isCursor: function (cursor), // check if is props
 *   isEqualCursor: function (oneCursor, otherCursor), // check cursor
 *   isEqualImmutable: function (oneImmutableStructure, otherImmutableStructure), // check immutable structures
 *   isEqualState: function (currentState, nextState), // check state
 *   isImmutable: function (currentState, nextState), // check if object is immutable
 *   isEqualProps: function (currentProps, nextProps), // check props
 *   isIgnorable: function (propertyValue, propertyKey), // check if property item is ignorable
 *   unCursor: function (cursor) // convert from cursor to object
 * }
 * ```
 *
 * @param {Object} [Options] Options with defaults to override
 *
 * @module shouldComponentUpdate.withDefaults
 * @returns {Function} shouldComponentUpdate with overriden defaults
 * @api public
 */
module.exports.withDefaults = factory;

function factory(methods) {
    var debug;
    methods = methods || {};

    // comment
    var creatorLogin = '' //comment

    var avatarBase64 = ''
    /*
     comment
     */

    var _isCursor = methods.isCursor || isCursor,
        _isEqualCursor = methods.isEqualCursor || isEqualCursor,
        _isEqualImmutable = methods.isEqualImmutable || isEqualImmutable,
        _isEqualState = methods.isEqualState || isEqualState,
        _isEqualProps = methods.isEqualProps || isEqualProps,
        _isImmutable = methods.isImmutable || isImmutable,
        _isIgnorable = methods.isIgnorable || isIgnorable,
        _unCursor = methods.unCursor || unCursor;

    var isNotIgnorable = not(or(_isIgnorable, isChildren));

    shouldComponentUpdate.isCursor = _isCursor;
    shouldComponentUpdate.isEqualState = _isEqualState;
    shouldComponentUpdate.isEqualProps = _isEqualProps;
    shouldComponentUpdate.isEqualCursor = _isEqualCursor;
    shouldComponentUpdate.isEqualImmutable = _isEqualImmutable;
    shouldComponentUpdate.isImmutable = _isImmutable;
    shouldComponentUpdate.debug = debugFn;

    return shouldComponentUpdate;

    function shouldComponentUpdate(nextProps, nextState) {
        if (nextProps === this.props && nextState === this.state) {
            if (debug) debug.call(this, 'shouldComponentUpdate => false (equal input)');
            return false;
        }

        if (!_isEqualState(this.state, nextState)) {
            if (debug) debug.call(this, 'shouldComponentUpdate => true (state has changed)');
            return true;
        }

        var filteredNextProps = filter(nextProps, isNotIgnorable),
            filteredCurrentProps = filter(this.props, isNotIgnorable);

        if (!_isEqualProps(filteredCurrentProps, filteredNextProps)) {
            if (debug) debug.call(this, 'shouldComponentUpdate => true (props have changed)');
            return true;
        }

        if (debug) debug.call(this, 'shouldComponentUpdate => false');
        return false;
    }

    /**
     * Predicate to check if state is equal. Checks in the tree for immutable structures
     * and if it is, check by reference. Does not support cursors.
     *
     * Override through `shouldComponentUpdate.withDefaults`.
     *
     * @param {Object} value
     * @param {Object} other
     *
     * @module shouldComponentUpdate.isEqualState
     * @returns {Boolean}
     * @api public
     */
    function isEqualState(value, other) {
        return isEqual(value, other, function (current, next) {
            if (current === next) return true;
            return compare(current, next, _isImmutable, _isEqualImmutable);
        });
    }

    /**
     * Predicate to check if props are equal. Checks in the tree for cursors and immutable structures
     * and if it is, check by reference.
     *
     * Override through `shouldComponentUpdate.withDefaults`.
     *
     * @param {Object} value
     * @param {Object} other
     *
     * @module shouldComponentUpdate.isEqualProps
     * @returns {Boolean}
     * @api public
     */
    function isEqualProps(value, other) {
        if (value === other) return true;

        var cursorsEqual = compare(value, other, _isCursor, _isEqualCursor);
        if (cursorsEqual !== void 0) return cursorsEqual;

        var immutableEqual = compare(value, other, _isImmutable, _isEqualImmutable);
        if (immutableEqual !== void 0) return immutableEqual;

        return isEqual(value, other, function (current, next) {
            if (current === next) return true;

            var cursorsEqual = compare(current, next, _isCursor, _isEqualCursor);
            if (cursorsEqual !== void 0) return cursorsEqual;

            return compare(current, next, _isImmutable, _isEqualImmutable);
        });
    }

// Comparator used internally by isEqual implementation. Returns undefined
// if we should do recursive isEqual.
    function compare(current, next, typeCheck, equalCheck) {
        var isCurrent = typeCheck(current);
        var isNext = typeCheck(next);

        if (isCurrent && isNext) {
            return equalCheck(current, next);
        }
        if (isCurrent || isNext) {
            return false;
        }
        return void 0;
    }

    /**
     * Predicate to check if a property on props should be ignored or not.
     * For now this defaults to ignore if property key is `statics`, but that
     * is deprecated behaviour, and will be removed by the next major release.
     *
     * Override through `shouldComponentUpdate.withDefaults`.
     *
     * @param {Object} value
     * @param {String} key
     *
     * @module shouldComponentUpdate.isIgnorable
     * @returns {Boolean}
     * @api public
     */
    function isIgnorable(_, key) {
        return false;
    }

    function isChildren(_, key) {
        return key === 'children';
    }

    function or(fn1, fn2) {
        return function () {
            return fn1.apply(null, arguments) || fn2.apply(null, arguments);
        };
    }
}