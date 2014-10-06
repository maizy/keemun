# Build .deb

## Build requirements

* keemun checkout (via `git clone ...` or [tarball](https://github.com/maizy/zaglushka/archive/master.tar.gz))
* sbt([deb package available](http://www.scala-sbt.org/0.13/tutorial/Installing-sbt-on-Linux.html))
* deb packages `dpkg`, `fakeroot`
* jdk 7 (openjdk or oracle-jdk)

## How to

```bash
# checkout keemun
cd keemun/
./build_dev.sh
dpkg -i target/keemun_X.Y.Z-SNAPSHOT_all.deb
```
where `X.Y.Z` - current project version

## Supported OS

* Ubuntu 12.04
 * Build tested on clean Ubuntu Server 12.04.5
* *TODO*: Ubuntu 14.04

Other .deb based OS also may work, but not tested.
