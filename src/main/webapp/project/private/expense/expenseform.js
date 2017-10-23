/**
 * Arquivo Controller em angular.
 * @author Samael Pereira Simões
 */
app.controller('ExpenseForm', [ '$scope', '$rootScope', '$location', 'toastr', 'ExpenseService',
					   function( $scope,  $rootScope,  $location, toastr, ExpenseService ) {
	
	$rootScope.activetab = $location.path();
	
	$scope.expenses = {}; // Model da lista de gêneros	
	$scope.selected = [];
	
	$scope.save = function(g) {			
		ExpenseService.save(g, function(response) {	
			
			$scope.load();
			$scope.expenses = null;
			toastr.success("Cadastrado com sucesso!");
		}, function(err){			
			toastr.error( 'Erro ' + err.data);
		})
	}
}]);

