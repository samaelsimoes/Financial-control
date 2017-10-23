/**
 * Arquivo Controller em angular.
 * @author Samael Pereira Simões
 */

app.controller('CostcenterGridCrtl', ['$scope', '$http', '$log','$rootScope', '$location', 'toastr', 'CostCenterService', '$timeout','uiGridConstants',
			  function($scope, $rootScope, $location, $http, $log, toastr, CostCenterService, timeout, uiGridConstants) {

	$scope.costcenters = []; // Model da lista de gêneros
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
				field: 'code',
				displayName: 'Código', 
				enableCellEdit: false, 
				type: "number"
			},
			{ 
				field: 'name',
				displayName: 'Nome', 
				enableCellEdit: false
			}
		]
	};
	    
	/* --- Server --- */ // Service
	$scope.load = function() { // load all		
		CostCenterService.query(function( costcenters ) {	

			$scope.costcenters = costcenters;
			$scope.gridOptions.data = costcenters;		 		
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
	
	$scope.editCostcenter = function ( genres, t ) {
		
		var row = $scope.rows[0].entity;
		$scope.update( row );
	};
	$scope.update = function ( row ) {
		CostCenterService.update ( row, function (response ) {
			toastr.success(response.message);
			$scope.load();
			window.setTimeout(function() {
				location.reload();
		    }, 2000);
		}, function ( err ){
			toastr.error( 'Erro ' + err.data);
		})
	};
	$scope.filter = function() {
        $scope.gridApi.grid.refresh();
    };
    $scope.singleFilter = function( renderableRows ) {
    	var matcher = new RegExp( $scope.filterValue );
    	renderableRows.forEach( function( row ) {
    		var match = false;
    		[ 'name', 'code'].forEach(function( field ) {
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
    	    	
    	CostCenterService.remove({ids: t}, function(response) {
    	
			toastr.success("Excluído com sucesso");
			
			$scope.load();
    	}), function(err) {
    		console.log(err);
    		toastr.error('Erro ' + err);
    	}
    };  
    $scope.load();
}])