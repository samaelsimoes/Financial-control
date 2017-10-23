/**
 * Arquivo Controller em angular.
 * @author Samael Pereira Simões
 */

app.controller('GenreFormCrtl', [ '$scope', '$rootScope', '$location', 'toastr', 'GenreService',
					    		function( $scope,  $rootScope,  $location, toastr, GenreService ){
	
    $rootScope.activetab = $location.path();
	$scope.selected = [];
  	
	$scope.save = function(g) {
		if ( g == undefined || g == "" ){
			
			toastr.warning("Gentileza preencher os campos obrigatório");	
		}else if( g != undefined || g != "" ){
			
			GenreService.save(g, function(response) {				
				toastr.success("Cadastrado com sucesso!");
				$scope.load();
				$scope.genre = null;
			}, function(err){			
				toastr.error( 'Erro ' + err.data);
			})
		}
	}
}]);

