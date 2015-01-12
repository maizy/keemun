# TODO: requirejs
utils = @keemun.utils
$ = jQuery
routes = keemun.routes


class SearchForm

  @COMPONENT = 'keemun_search_form'

  constructor: (@_$wrapper, @_debug = false) ->
    @_reps = []
    @_repsKeys = []
    if not @_$wrapper or @_$wrapper.length == 0
      mes = 'Search form wrapper not found'
      utils.error mes
      throw "#{SearchForm.COMPONENT}: #{mes}"
    $(@init.bind @)

  getWrapper: -> @_$wrapper

  subSelect: (name) -> @getWrapper().find ".#{SearchForm.COMPONENT}--#{name}"

  getForm: -> @subSelect 'element'

  getSubmitButton: -> @getForm().find ':submit'

  getRealForm: -> @subSelect 'real-form'

  setReps: (reps) ->
    @_reps = reps
    @_repsKeys = "repo: #{reps.join ' :repo'}"
    @

  getRepsKeys: -> @_repsKeys

  init: ->
    if @_debug
      utils.debug 'Start in debug mode'
      do @getRealForm().show
    @getSubmitButton().prop('disable', true)
    do @_loadReps
    do @_bindForm
    null

  _loadReps: ->
    r = routes.controllers.Repositories.list()
    $.ajax
      url: r.url
      method: r.method
      success: (data) =>
        utils.debug data
    @

  _bindForm: ->


@keemun or= {}
@keemun.SearchForm = SearchForm
