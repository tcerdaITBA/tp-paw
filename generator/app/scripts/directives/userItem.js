'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('userItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/userItem.html',
            scope: {user: '='},
            controller: ['$scope', '$window', function($scope, $window) {
                var user = $scope.user;
				
				$scope.sendMail = function(event) {
					window.location.href = "mailto:" + user.email;
					event.stopPropagation();
				};
            }]
        }
    });
});