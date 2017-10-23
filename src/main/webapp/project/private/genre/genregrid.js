/**
 * Arquivo Controller em angular.
 * @author Samael Pereira Simões
 */

app.controller('GenreGridCrtl', ['$scope', '$http', '$log','$rootScope', '$location', 'toastr', 'GenreService', '$timeout','uiGridConstants',
	function($scope, $rootScope, $location, $http, $log, toastr, GenreService, timeout, uiGridConstants) {
	
	 // Model da lista de gêneros
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
		GenreService.query(function( genres ) {	

			$scope.genres = genres;
			$scope.gridOptions.data = genres;	
			 		
		}, function( error ) {			 
			toastr.error( error );
		});
	};
	//-- end load 

	// ---- ROWS checkeds or unchecked	
	$scope.gridOptions.onRegisterApi = function ( gridApi ) {		
			
		$scope.gridApi = gridApi;
		$scope.gridApi.grid.registerRowsProcessor( $scope.singleFilter, 200 ); // Filter grid
			
		gridApi.selection.on.rowSelectionChanged( $scope, function( row ) {
			
			var msg = row.isSelected; //one selected true or false
			if ( msg ) {
				$scope.rows.push( row );
			} else {				
				$scope.rows.splice( $scope.rows.indexOf ( row ));
			}
		});
	};
	// --- end rows ---
	
	// --- Edit ---
	$scope.editGenre = function( genres, i ) {
		var row = $scope.rows[0].entity;
		$scope.update( row );		
    };  
    // --- end edit
	
    //---- row update grid
	$scope.update = function ( row ) {
		console.log(row);
		GenreService.update ( row, function( response ) {
			
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
    	console.log(t);
    	GenreService.remove({ids: t}, function(response) {
    	
			toastr.success("Excluido com sucesso! ");
			$scope.load();
    	}), function(err) {
    		console.log(err);
    		toastr.error('Erro ' + err);
    	}
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
    
	$scope.load();
}])


