# API

Those backends used internaly by hedgehog pages. But you can call them directly if you want.
Methods arguments & response params will be compatible from version to version.

## Common fields

All objects contains unique `id` field. This field garantee to be unique, but field format
may be changed later.

## GET /repositories

  * group
    * by_account
    * by_owner
  * sort
    * repo_name
    * owner_name
  * direction
    * asc
    * desc

Without grouping:
```javascript
{
    "repositories": [
        {
            //repo object
        },
        // ...
    ],
    "params": {
        "sort": "repo_name",
        "direction": "asc",
        "group": null
    }
}
```

With grouping:
```javascript
{
    "repositories_groups": [
        {
            "id": "group_code",
            "name": "Group Name",
            "repositories": [
                {
                    // repo object
                },
                // ...
            ]
        }
        // ...
    ],
    "params": {
        "sort": "repo_name",
        "direction": "asc",
        "group": "by_account"
    }
}
```

## GET /configs
```javascript
{
    "sources": [
        {
            "account": {
                // short account object
            },
            "include_private_repos": true
        },
        // ...
    ]
}

```

## Common objects

## Repo

_See [Scala Repository model](Models.md#github_repo)._

```javascript
{
    "id": "user/repo",
    "name": "repo",
    "full_name": "user/repo",
    "description": "Some words",
    "primary_lang": {
        // programming lang object
    },
    "langs_stat": [ // order by bytes
        {
            "bytes": 847475,
            "lang": {
                // programming lang object
            }
        },
        // ...
    ],
    "repo_url": "https://github.com/user/repo"
    "profile_url": "https://github.com/user/repo"
}
```

## Account

_See [Scala GitHubAccount model](Models.md#github_account)._

short:

```javascript
{
    "id": "user",
    "name": "user",
    "profile_url": "https://github.com/user",
    "type": "corp" // or "user"
}
```


full:

```javascript
{
    "id": "maizy",
    "name": "maizy",
    "profile_url": "https://github.com/maizy",
    "avatar_url": "http://.../logo.png",
    "type": "corp" // or "user"
}
```

## Programming lang

_See [Scala ProgrammingLang model](Models.md#github_account)._

```javascript
{
    "id": "Python",
    "name": "Python"
}
```
