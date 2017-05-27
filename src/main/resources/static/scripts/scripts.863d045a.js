function isUndefinedOrNull(a){return void 0===a||null===a}angular.module("petstoreFrontApp",["ngCookies","ngResource","ngRoute","ngSanitize","smart-table"]).constant("CONSTANTS",function(){return{USERS_DOMAIN:"",SUFFIXE_DOMAIN:".do"}}()).config(["$routeProvider","$locationProvider",function(a,b){b.hashPrefix(""),a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl",controllerAs:"about"}).otherwise({redirectTo:"/"})}]),angular.module("petstoreFrontApp").controller("MainCtrl",["PetsApiFactory",function(a){var b=this;this.displayListPets=[],this.listPets=[],this.lineByPage=5,this.init=function(){this.listPets()},this.listPets=function(){a.list().then(function(a){isUndefinedOrNull(a)||(b.listPets=a,b.displayListPets=a)},function(a){})}}]),angular.module("petstoreFrontApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),angular.module("petstoreFrontApp").factory("PetsApiFactory",["$http","CONSTANTS",function(a,b){return{save:function(c){return a.post(b.USERS_DOMAIN+"/pet"+b.SUFFIXE_DOMAIN,c).then(function(a){return a.data},function(a){return console.log(a),a})},list:function(){return a.get(b.USERS_DOMAIN+"/pet/list"+b.SUFFIXE_DOMAIN).then(function(a){return a.data},function(a){return console.log(a),a})}}}]),angular.module("petstoreFrontApp").run(["$templateCache",function(a){"use strict";a.put("views/about.html","<p>This is the about view.</p> "),a.put("views/main.html",'<div ng-init="main.init()" class="control"> <div class="row action-bar"> <div class="col-xs-12"> <button class="btn btn-primary" type="button" ng-click=""><span><i class="fa fa-plus" aria-hidden="true"></i> Add Pet</span></button> </div> </div> <div class="row"> <div class="col-xs-12"> <table st-table="main.displayListPets" st-safe-src="main.listPets" class="table table-striped table-no-border"> <thead> <tr> <th class="sortable" st-sort="name">Name</th> <th class="sortable" st-sort="status">Status</th> <th class="sortable" st-sort="category">Category</th> <th class="sortable" st-sort="tags">Tags</th> </tr> <tr> <th> <input st-search="name" placeholder="name" class="input-sm form-control" type="search"> </th> <th> <input st-search="status" placeholder="status" class="input-sm form-control" type="search"> </th> <th> <input st-search="category" placeholder="category" class="input-sm form-control" type="search"> </th> <th> <input st-search="tags" placeholder="tags" class="input-sm form-control" type="search"> </th> </tr> </thead> <tbody> <tr ng-show="main.displayListPets == null || main.displayListPets.length == 0"> <td colspan="4"> Pas de données </td> </tr> <tr ng-repeat="pet in main.displayListPets"> <td>{{pet.name}}</td> <td>{{pet.status}}</td> <td>{{pet.category}}</td> <td>{{pet.tags}}</td> </tr> </tbody> <tfoot> <tr> <td colspan="4" class="text-center"> <div st-pagination="" st-items-by-page="main.lineByPage" st-template="views/partials/pagination.html"></div> </td> </tr> </tfoot> </table> </div> </div> </div>'),a.put("views/partials/pagination.html",'<div class="pagination" ng-if="pages.length >= 2"> <ul class="pagination"> <li> <a class="clickable" ng-click="selectPage(1)" aria-label="First"> <span aria-hidden="true">Début</span> </a> </li> <li> <a><i class="clickable fa fa-arrow-left" ng-click="selectPage(currentPage-1)" aria-hidden="true"></i></a> </li> <li ng-repeat="page in pages" ng-class="{active: page==currentPage}"><a class="clickable" ng-click="selectPage(page)">{{page}}</a></li> <li> <a><i class="clickable fa fa-arrow-right" ng-click="selectPage(currentPage+1)" aria-hidden="true"></i></a> </li> <li> <a class="clickable" href="" ng-click="selectPage(numPages)" aria-label="Last"> <span aria-hidden="true">{{numPages}}</span> </a> </li> </ul> </div>')}]);