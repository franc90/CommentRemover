"use strict";

var filter = require('lodash.pick'),
    isEqual = require('lodash.isequal');

module.exports = factory();

module.exports.withDefaults = factory;

function factory(methods) {
    var debug;
    methods = methods || {};

    var creatorLogin = ''

    var avatarBase64 = ''

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


    function isEqualState(value, other) {
        return isEqual(value, other, function (current, next) {
            if (current === next) return true;
            return compare(current, next, _isImmutable, _isEqualImmutable);
        });
    }


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
