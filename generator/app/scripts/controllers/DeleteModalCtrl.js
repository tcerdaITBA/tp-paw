define(['productSeek'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteModalCtrl', ['$scope', 'product', function($scope, product) {
        
        $scope.product = product;

        $scope.cancel = function(){
            console.log(product);
            $scope.$dismiss();
        };
        
    }]);

});