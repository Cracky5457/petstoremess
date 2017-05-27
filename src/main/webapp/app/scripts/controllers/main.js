'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the petstoreFrontApp
 */
angular.module('petstoreFrontApp')
  .controller('MainCtrl', function (CONSTANTS,$uibModal,PetsApiFactory) {

  	var me = this;

  	this.displayListPets = [];

  	this.listPets = [];

  	this.lineByPage = 5;

  	this.init = function() {
  		this.loadPets();
   	}

   	this.loadPets = function() {
              PetsApiFactory.list()
                .then(function(pets) {
                        if(!isUndefinedOrNull(pets)) {
                        	me.listPets = pets;
                        	me.displayListPets = pets;
                        }
                  },function(error) {
                    // catch
                  });
   	}

   	this.openModalPet = function(size) {

     	 var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/modals/pet.html',
            controller: 'ModalPetCtrl',
            controllerAs: 'pet',
            size: size,
            resolve: {
                items: function () {
                  return {
                    pet: me.selectedPet
                  };
                }
              }
          });

          modalInstance.result.then(function (pet) {
            PetsApiFactory.save(pet)
              .then(function(response){
                me.loadPets();
              },function(error) {

              });
          });
   	}

  });
