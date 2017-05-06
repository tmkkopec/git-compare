# git-compare

## Requirements
* node.js
* npm (included in node.js)
* bower (`npm install -g bower`)

## Usage

* `git clone` repo

### Frontend development

* `cd $project_dir/src/main/fronted`
* `npm install` to fetch dependencies for server side
* `bower install` to fetch dependencies for client side
* `npm run build` to build javascript code

All output code is put under `src/main/resources/static`.

### Backend development

* `cd $project_dir`
* in order to start server 
    1. just run in IDE [main class](https://github.com/tmkkopec/git-compare/blob/master/src/main/java/org/tai/GitCompareApplication.java) OR
    2. at the first terminal, start Gradle build as a continuous task: `gradle build --continuous` AND 
    at the second terminal, start the Gradle bootRun task: `gradle bootRun` 
