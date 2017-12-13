define(['controllers/IndexCtrl', 'angular-mocks'], function() {
	describe('Index Controller', function() {
		var controller, scope;
		
		beforeEach(module('productSeek'));
		
		beforeEach(inject(function($injector, $rootScope, $controller) {
			scope = $rootScope.$new();
			controller = $controller('IndexCtrl', { $scope: scope });
		}));
		
		describe('$scope.isLoggedIn', function() {
			it('should be false beforehand', function() {
				expect(scope.isLoggedIn).toBe(false);
			});
			
		});
	});
});