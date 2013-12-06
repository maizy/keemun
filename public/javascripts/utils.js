if (typeof window.hedgehog === 'undefined') { window.hedgehog = {}; }
if (typeof window.hedgehog.utils === 'undefined') {
window.hedgehog.utils = (function() {
    'use strict';

    var exp = {};

    function toConsole(handler, msg) {
        if (!_.isUndefined(window.console) && _.isFunction(window.console[handler])) {
            window.console[handler](msg);
        }
    }

    exp.error = function (msg) {
        toConsole('error', msg);
    };

    exp.debug = function (msg) {
        toConsole('debug', msg);
    };

    return exp;
})();
}
