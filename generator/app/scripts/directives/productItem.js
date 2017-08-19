'use strict';
define(['productSeek', 'controllers/ProductItemCtrl'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '='},
            controllerUrl: '/controllers/ProductItemCtrl.js'
        }
    });
});