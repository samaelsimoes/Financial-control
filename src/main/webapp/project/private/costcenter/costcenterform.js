/**
 * Arquivo Controller em angular.
 * @author Samael Pereira Simões
 */

app.controller('CostcenterFormCrtl', [ '$scope', '$rootScope', '$location', 'toastr', 'CostCenterService',
			  function( $scope,  $rootScope,  $location, toastr, CostCenterService ){

	$rootScope.activetab = $location.path();
	$scope.costcenters = []; 
	$scope.selected = [];
	
	$scope.save = function(ce) {
		if ( ce == undefined || ce == "" ){
			
			toastr.warning("Gentileza preencher os campos obrigatório");	
		}else if( ce != undefined || ce != "" ){				
			CostCenterService.save(ce , function(response) {		
				
				toastr.success("Cadastrado com sucesso!");
				$scope.load();				
				$scope.costcenter = null;
			}, function(err){			
				toastr.error( 'Erro ' + err.data);
			})
		} 
	}
}])
