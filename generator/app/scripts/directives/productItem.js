'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'controllers/DeleteModalCtrl', 'controllers/CollectionModalCtrl'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/productItem.html',
            scope: {product: '=', hideCategory: '=', hideDelete: '=', onVote: '&', onAdd: '&', onDelete: '&', borderHover: '=', order: '=', orderBy: '='},
            controller: ['$scope', '$location', '$route', 'authService', 'restService', 'modalService', 'snackbarService', 'defaultSortCriteria', function($scope, $location, $route, authService, restService, modalService, snackbarService, defaultSortCriteria) {

                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                $scope.loggedUser = authService.loggedUser;
                
                $scope.directToCategory = function() {
                    console.log($scope.orderBy);
                    var orderBy = $scope.orderBy || defaultSortCriteria.orderBy;
                    var order = $scope.order || defaultSortCriteria.order;
                    $location.url('/?category=' + product.category + '&orderBy=' + orderBy + '&order=' + order);
                };
        
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };
                
                $scope.isLoggedIn = authService.isLoggedIn();
                
                $scope.$on('user:updated', function() {
                    if (!authService.isLoggedIn()) {
                        $scope.product.voted = false;
                        $scope.isLoggedIn = false;
                        $scope.loggedUser = null;
                    }
                    else
                        $route.reload(); // Must retrieve product again in order to know whether it was voted by the new user or not
                });
                
                $scope.toggleVote = function() {
                    if ($scope.isLoggedIn) {
                        $scope.product.voted = !$scope.product.voted;
						var voteSum = $scope.product.voted ? 1 : -1;
						$scope.product.voters_count += voteSum;
                        $scope.onVote({voted: $scope.product.voted});
                        
                        if ($scope.product.voted) {
                            restService
								.voteProduct(product.id)
								.catch(function() {revertVote(voteSum)});
                        }
                        else {
                            restService
								.unvoteProduct(product.id)
								.catch(function() {revertVote(voteSum)});
                        }
                    }
                };
				
				function revertVote(voteSum) {
					$scope.product.voters_count -= voteSum;
					$scope.product.voted = !$scope.product.voted;
					// TODO snackbar
					console.log("no se pudo votar/desvotar");
				}
                
                $scope.showCollectionModal = function() {
                    var modal = modalService.collectionModal($scope.product, authService.loggedUser.collections);
                    
                    modal.result.then(function(collection) {
                        if (collection)
                            $scope.onAdd({'collection': collection});
                    });
                };
                
                $scope.deleteModal = function() {
                    var modal = modalService.deleteModal($scope.product);
                    modal.result.then(function(isDeleted) {
                        if (isDeleted)
                            $scope.onDelete();
                    });
                };
                
                if ($scope.borderHover) {
                    $(".product-list-item").hover(function(){
                        $(this).css("border-color", "#33bb9c");
                        $(this).css("cursor", "pointer");
                        }, function(){
                        $(this).css("border-color", "#f3f3f3");
                    });
                }

                $scope.signInModal = modalService.signInModal;
                $scope.signUpModal = modalService.signUpModal;
            }]
        }
    });
});