app.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);

app.controller('UserGridCrtl', ['$scope', '$http', '$log','$rootScope', '$location', 'toastr', 'UserService', '$timeout','uiGridConstants',
	function($scope, $rootScope, $location, $http, $log, toastr, UserService, timeout, uiGridConstants) {
	
	$scope.users = [];
	$scope.rows = [];
	
	$scope.gridOptions = {
			 
		rowHeight: 35,         
		selectionRowHeaderWidth: 35,
		enableRowSelection: true,
		enableSelectAll: true,
		showGridFooter:false,
		paginationPageSize: 10,
		selectedItems: $scope.mySelections,
		enablePaginationControls: false,
	    enableFiltering : false,
	    
		columnDefs: [		 
			 { 
				field: 'name', 
				displayName: 'Nome', 
				enableCellEdit: false
			 },
			 {
				 field: 'typeUser',
				 displayName: 'Tipo',
				 cellTemplate: '<div class="ui-grid-cell-contents"><span ng-show="grid.getCellValue(row, col) === 0"> Administrador </span> <span ng-show="grid.getCellValue(row, col) === 1"> Operacional</span> </div>',
				 enableCellEdit: false
			 },
			 {
				 field: 'userLogin',
				 displayName: 'Login',
				 enableCellEdit: false
			 }
		]
	};
	
	/* --- Server --- */ // Service
	$scope.load = function() { // load all		
		UserService.query(function( users ) {	

			$scope.users = users;
			$scope.gridOptions.data = users;	
			 		
		}, function( error ) {			 
			toastr.error( error );
		});
	};
	//-- end load 
	
	// ---- ROWS checkeds or unchecked	
	$scope.gridOptions.onRegisterApi = function ( gridApi ) {		
			
		$scope.gridApi = gridApi;
		$scope.gridApi.grid.registerRowsProcessor( $scope.singleFilter, 200 ); // FILTER GRID
		
		gridApi.selection.on.rowSelectionChanged( $scope, function( row ) {
			
			var msg = row.isSelected; //one selected true or false
			if ( msg ) {
				$scope.rows.push( row );
			} else {				
				$scope.rows.splice( $scope.rows.indexOf ( row ));
			}
		});
	};
	// --- end rows --
	
	// --- Edit ---
	$scope.editUser = function( genres, i ) {
		var row = $scope.rows[0].entity;
		$scope.update( row );		
    };  
    // --- end edit
   
  	//---- row update grid
	$scope.update = function ( row ) {
		UserService.update ( row, function( response ) {			
			toastr.success(response.message);
			$scope.load();
			window.setTimeout(function() {
				location.reload();
		    }, 2000);
		}, function (err) {	
			toastr.error( 'Erro ' + err.data);
		})
	};
    
	$scope.aceptdelet = function () {
    	
    	var rows = $scope.gridApi.selection.getSelectedRows();
    	var daterowsid = [];
    	
        for (var i=0; i < rows.length; i++) {
        	daterowsid.push(rows[i].oid);
        }
    	$scope.deletrows( daterowsid );
    };
    
    // ---=== Delet rowsw
    $scope.deletrows = function( ids ) {

    	var t = ids;
    	    	
    	UserService.remove({ids: t}, function(response) {
    		
			toastr.success("Excluido com sucesso! ");
			$scope.load();
    	}), function(err) {
    		
    		console.log(err);
    		
    		toastr.error('Erro ' + err);
    		alert(err   + '    teste ');
    	}
    };
	
    $scope.filter = function() {
    	$scope.gridApi.grid.refresh();
	};
	
	$scope.singleFilter = function( renderableRows ) {
		var matcher = new RegExp( $scope.filterValue );
		renderableRows.forEach( function( row ) {
			var match = false;
			[ 'name', 'typeUser' , 'userLogin'].forEach(function( field ) {
				if ( row.entity[field].toString().match(matcher) ) {
					match = true;
				}
			});
			if ( !match ) {
				row.visible = false;
			}
	    });		
	  return renderableRows;
	};    	
	$scope.load();
}])
