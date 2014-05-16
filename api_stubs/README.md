# API stubs

Emulate github api.

### Requirements
* python 2.7
* [pip](http://pip.readthedocs.org/en/latest/installing.html) (python package tools)
* [zaglushka.py](https://github.com/maizy/zaglushka) (`pip install git+https://github.com/maizy/zaglushka.git`)

### Run
```
zaglushka.py --ports 8000 --config=api_stubs/config.json
```

Run this app with special value in `github.api_url`. For example:
```
play -Dgithub.api_url=http://localhost:8000 run
```
