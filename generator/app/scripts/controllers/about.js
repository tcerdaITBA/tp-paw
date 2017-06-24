define(['productSeek'], function(productSeek) {

    'use strict';
    productSeek.controller('about', function($scope) {
        $scope.abouts = [{text: 'Product Seek', likes: 0}];       

        $scope.addAbout = function() {
            $scope.abouts.push({text: $scope.aboutText, likes: 0});
            $scope.aboutText = '';
        };

        $scope.like = function(about) {
            about.likes += 1;
        }

        $scope.unlike = function(about) {
            if (about.likes > 0)
                about.likes -= 1;
        }
    });

});
