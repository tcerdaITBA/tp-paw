'use strict'
define(['productSeek'], function(productSeek) {
    
    productSeek.controller('ProductItemCtrl', ['$scope', '$location', function($scope, $location) {
        var product = $scope.product;
        
        var directToCategory = function() {
            $location.path('#/?category=' + product.category);
        }
    }]);
});