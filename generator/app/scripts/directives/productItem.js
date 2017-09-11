'use strict';
define(['productSeek', 'services/authService'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '=', borderHover: '='},
            controller: ['$scope', '$location', '$route', 'authService', 'restService', function($scope, $location, $route, authService, restService) {
                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                
                $scope.directToCategory = function() {
                    $location.url('/?category=' + product.category);
                };
        
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };
                
                $scope.isLoggedIn = authService.isLoggedIn();
                
                $scope.$on('user:updated', function() {
                    if (!authService.isLoggedIn()) {
                        $scope.product.voted = false;
                        $scope.isLoggedIn = false;
                    }
                    else
                        $route.reload(); // Must retrieve product again in order to know whether it was voted by the new user or not
                });
                
                $scope.toggleVote = function() {
                    if ($scope.isLoggedIn) {
                        $scope.product.voted = !$scope.product.voted;

                        if ($scope.product.voted) {
                            $scope.product.voters_count += 1;
                            restService.voteProduct(product.id);
                        }
                        else {
                            $scope.product.voters_count -= 1;
                            restService.unvoteProduct(product.id);
                        }
                    }
                }
                
                if ($scope.borderHover) {
                    $(".product-list-item").hover(function(){
                        $(this).css("border-color", "#33bb9c");
                        }, function(){
                        $(this).css("border-color", "#f3f3f3");
                    });
                }
            }]
        }
    });
});