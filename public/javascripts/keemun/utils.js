if (typeof window.keemun === 'undefined') { window.keemun = {}; }
if (typeof window.keemun.utils === 'undefined') {
window.keemun.utils = (function() {
    'use strict';

    var exp = {};
    var toConsole = _.curry(function (handler, msg) {
        if (!_.isUndefined(window.console) && _.isFunction(window.console[handler])) {
            window.console[handler](msg);
        }
    });

    exp.error = toConsole('error');
    exp.debug = toConsole('debug');

    return exp;
})();
}
