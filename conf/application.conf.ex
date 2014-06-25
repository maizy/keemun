# This is the main configuration file for the application.
# Syntax - HOCON: https://github.com/typesafehub/config#examples-of-hocon


# Sources
# -------

github {
    api_url = "https://api.github.com"

    # App token for github api access
    # -------------------------------
    # Generate it on: [https://github.com/settings/applications]
    # Scope `public_repo` is required.
    # For fetching private organization repositories also add `repo` scope.
    app_token = "aaaabbbbccccddddeeeeffff0000111122223333"

    # Sources for searching
    # ---------------------
    # key - user/org name
    # value - options, use `{}` if no additional options used
    sources: [
        # users
        torvalds  # simply add one name per line

        # or add user with some options
        {
            name: maizy

            ## Avalable options:

            # Include private repos
            # ---------------------
            # `public_repo` scope required for using.
            # By default: false
            include_private_repos = true
        }

        # organizations
        {
            name = playframework

            # mark as organization
            org = true

            # all other options the same as for users
        }
    ]
}


# Application settings
# --------------------
application : {

    # Secret key
    # ----------
    # The secret key is used to secure cryptographics functions.
    # If you deploy your application to several instances be sure to use the same key!
    secret = "p:8d6@s;Dndb[aQ^yjvRGmv308R4KWk3_LRST2sMx7gxDJTNPXIaVjHVEnAjg^KS"

    # The application languages
    langs = "ru,en"
    lang.cookie = "lang"
}


# Logger
# ------
# You can also configure logback (http://logback.qos.ch/), by providing a logger.xml file in the conf directory .
logger: {
    # Root logger:
    root: ERROR

    # Logger used by the framework:
    play: INFO

    # Logger provided to your application:
    application: DEBUG
}