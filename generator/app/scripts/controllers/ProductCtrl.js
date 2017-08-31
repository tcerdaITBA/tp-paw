'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.controller('ProductCtrl', ['$scope', 'product', function($scope, product) {
		$scope.product = product;
        $scope.creator = product.creator;
        $scope.comments = product.comments;
        $scope.video_ids = product.video_ids;
        $scope.image_urls = product.image_urls;

        $scope.slickConfig = {
		  enabled: true,
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1,
		};
    }]);
});
