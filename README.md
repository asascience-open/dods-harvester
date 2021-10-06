# dods-harvester

#### Build, deploy, and run
```bash
./build.sh && ./push-deploy.sh
```
Then `ssh` to `maracoos-tds-harvest` and
```bash
cd /opt/dods-harvester
./unpack-deploy.sh && ./run-harvester.sh
```
