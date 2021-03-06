# ElasticSearch Wrapper API

This is a small API wrapping searches towards ElasticSearch.

The ES is assumed to be running on `localhost:9200` and is not included in this repository.

This application is using the following technologies:
* Java8
* Maven
* JAX-RS
* Wildfly 16

## Setup
Clone repo from GitHub:
```shell script
git clone git@github.com:EvelinFoxell/ElasticSearchWrapperAPI.git
```

Build the project:
```shell script
mvn clean package
```
Run your ElasticSearch cluster.

## Deployment
This application is designed to run on a JBOSS Wildlfy server.

Copy, symlink or whichever other method of your choice the war into your deployment folder:
```shell script
ln -s {path to project root}/target/ElasticSearchWrapper.war {path to wildfly server root}/standalone/deployments/
```


## API map

```
- /                                   - GET         Gives you a html page
- /api/v1                             - GET         REST API declaration
- /api/v1/searchMatchWithSentiment    - GET         Entrypoint for searching on keywords
  Params:
    - query                           - String      Required
    - sentiment                       - String      Not Required
  Model:
    - score                           - BigDecimal
    - id                              - Int
    - body                            - String
    - title                           - String
    - sentiment                       - String
    - keyPhrases                      - Array
      - value                         - String
```
Example URL if you deploy war into `{Wildfly_home}/standalone/deployments` without renaming:
```
http://localhost:8080/ElasticSearchWrapper/api/v1/
```
