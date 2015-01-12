toConsole = _.curry (handler, msg) ->
  if window.console? and _.isFunction(window.console[handler])
    window.console[handler] msg

exports =
  error: toConsole 'error'
  debug: toConsole 'debug'

@keemun or= {}
@keemun.utils = exports  # TODO: require js
