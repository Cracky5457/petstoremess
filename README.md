# petstore

This project is generated with [yo angular generator](https://github.com/yeoman/generator-angular)
version 0.16.0. and Spring boot

## development

### front
`cd src\main\webapp`

`npm install`
`bower install`

Then run the server with `grunt serve` and develop on localhost:900

### back

`maven update` the project

Don't forget to exlude "node_modules" from ecliplse ( properties -> resource fitlers -> add filter -> exclude all "node_modules" folder recursive )

`run as spring boot application`

## build

Build the front

`cd src\main\webapp`
`grunt build`

F5 eclipse project

Build the war

`Run as Maven build ...` with goal `clean install`

## Testing

Running `grunt test` will run the unit tests with karma.
