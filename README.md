# TV SHOW application
> Android application build it  with kotlin that TV Shows and create new Tv Shows and manage your favorite TV Show
## Demo
<img src="https://github.com/liodali/TvShowApp/blob/main/demo_tv_show_app.gif?raw=true" alt="TV Show app"><br>


## Build
* should use Android Studio 2020.3.1

## Build Preparation
1) in local.properties ,should be add 
```
serverUrl=https://...
client_key=clientKey
application_id=idKey
```
2) download schema.json in core/src/main/graphql using this command
```shell
./gradlew downloadApolloSchema --endpoint=https://server/graphql \
--schema=core/src/main/graphql/schema.json \
--header="X-Parse-Application-Id : putProperAppIDKey" \
--header="X-Parse-Client-Key : putProperClientKey"
```

3) build project from IDE or  run ./gradlew build


##### In this project, we implement the  clean architecture
* we have 3 layer:

    * <srong>App module </string>  : This module contains all of the code related to the UI/Presentation layer such as ui that compose pages and component,viewModel for each page ,dependency injection,models
    * <srong>Core</string> : holds all concrete implementations of our repositories,usecaes and other data sources like  network
    * <srong>Domain module </string>  : contain all interfaces of repositories ,usecase and data classes


> I used jetpack compose (UI toolkit)  build user interfaces

> I used hilt as dependency injection for this project

> I used apollo client to connect to backend graphql and flowAPI to collect data

