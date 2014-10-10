# API

Those backends used internaly by keemun pages. But you can call them directly if you want.
Methods arguments & response params will be compatible from version to version.

## Common fields

All objects contains unique `id` field. This field garantee to be unique, but field format
may be changed later.

## GET /repositories

  * group
    * by_primary_lang
    * by_owner
  * sort
    * repo_name
    * owner_name
  * direction
    * asc (default)
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
                // account object (without avatar & some other additional info)
            },
            "include_private_repos": true
        },
        // ...
    ]
}

```

## GET /statistics

```json
{
    "repositories": {
        "total": 42,
    },
    "sources": {
        "total": 12,
        "user": 10,
        "org": 2
    }
}
```

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
}
```

## Account

_See [Scala Account model](Models.md#account)._

```javascript
{
    "id": "user",
    "name": "user",
    "profile_url": "https://github.com/user",
    "type": "org", // or "user"
    "avatar_url": "http://.../logo.png" // or null
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
