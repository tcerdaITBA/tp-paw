define(['productSeek'], function(productSeek) {
    'use strict';
	productSeek.directive('loading', ['$http', function($http) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                scope.isLoading = function () {
                    return $http.pendingRequests.length > 0;
                };

                scope.$watch(scope.isLoading, function (isLoading) {
                    isLoading ? element.show() : element.hide();
                });
            }
		};
	}]);
});
