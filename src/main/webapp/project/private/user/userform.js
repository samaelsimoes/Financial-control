app.controller('UserFormCrtl', [ '$scope', '$rootScope', '$location', 'toastr', 'UserService',
					      function( $scope,  $rootScope,  $location, toastr, UserService ){
	
	$rootScope.activetab = $location.path();
	$scope.users = []; // Model da lista de gêneros
	$scope.selected = [];
	  	
	$scope.save = function(us) {
		if ( us == undefined || us== "" ){
			
			toastr.warning("Gentileza preencher os campos obrigatório");	
		}else if( us != undefined || us != "" ){			
			UserService.save(us, function(response) {				
				toastr.success("Cadastrado com sucesso!");
				$scope.load();
				$scope.user = null;
			}, function(err){			
				toastr.error( 'Erro ' + err.data);
			})
		}
	}
}]);