app.controller('ExpenseGridCrtl', ['$scope','$http','uiGridExporterConstants', '$log', '$rootScope', 'ExpenseService', '$location', 'toastr', 
	'UserService', 'GenreService', 'CostCenterService', 'uiGridConstants', 
			   
	function($scope, $http, uiGridExporterConstants, $log, $rootScope, ExpenseService, $location,  toastr,  
			 UserService, GenreService, CostCenterService,  uiGridConstants) {

	now = new Date;
	dayName = new Array("domingo", "segunda", "terça", "quarta", "quinta", "sexta", "sábado");
	monName = new Array("janeiro", "fevereiro", "março", "abril", "maio", "junho", "agosto", "outubro", "novembro", "dezembro");
	var dia = ("Data: " + now.getDate () + " de " + monName [now.getMonth() ] + " de " + now.getFullYear ());
	
	$scope.expense = [];
	$scope.rows = [];
	$scope.users = [];
	$scope.costcenters = [];
	$scope.typegenres = [];  		
	
    exporterCsvFilename: 'despesas.csv';
    enableGridMenu: true;
	
	$scope.loadSelects = function() {	
		UserService.query(function( users ) {			
			$scope.users = users;	
		}, function( error ) {				
			toastr.error( error );
		});
		
		GenreService.query(function( genres ) {				
			$scope.typegenres = genres;	
		}, function( error ) {		
			toastr.error( error );
		});
		
		CostCenterService.query(function( costcenters ) {				
			$scope.costcenters = costcenters;
		}, function( error ) {			 
			toastr.error( error );
		});
	}
	
	$scope.gridOptions = {
			 
		rowHeight: 35,         
		selectionRowHeaderWidth: 35,
		enableRowSelection: true,
		enableColumnResizing: true,
		multiSelect: true,
		enableSelectAll: true,
		showGridFooter:false,
		paginationPageSize: 10,
		selectedItems: $scope.mySelections,
		enablePaginationControls: false,
	    enableFiltering : false,
	    enableSelectionBatchEvent: true,
	    exporterOlderExcelCompatibility: true,

		columnDefs: [	
			{ 
				field: 'code',
				exporterSuppressExport: false,
				displayName: 'Número', 
				enableCellEdit: false, 
				type: "number",
				
				cellTooltip: 
				function( row, col ) {
				  return 'Número: ' + row.entity.code;
				}, headerTooltip: 
				function( col ) {
					return 'Titulo: ' + col.displayName;
				}
			},
			{ 
				field: 'userOid', 
				exporterSuppressExport: false,
				displayName: 'Usuario', 
				enableCellEdit: false,
				
				cellTooltip: 
				function( row, col ) {
				  return 'Usuario: ' + row.entity.userOid;
				}, headerTooltip: 
				function( col ) {
					return 'Titulo: ' + col.displayName;
				}
			},	
			{ 
				field: 'description',
				exporterSuppressExport: true,
				displayName: 'Descrição', 
				enableCellEdit: false,
				
				cellTooltip: 
				function( row, col ) {
			      return 'Descrição: ' + row.entity.description;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			},
			{ 
				field: 'value', 
				exporterSuppressExport: true,
				displayName: 'Valor R$', 
				enableCellEdit: false,
				
				cellTooltip: 
				function( row, col ) {
			      return 'Valor: R$' + row.entity.value;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			},
			{ 
				field: 'paidValue', 
				exporterSuppressExport: false,
				displayName: 'Valor Adiantamento R$', 
				enableCellEdit: false,
				
				cellTooltip: 
				function( row, col ) {
			      return 'Valor de Adiantamento: R$ ' + row.entity.paidValue;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			},
			{ 
				field: 'date', 
				exporterSuppressExport: true,
				displayName: 'Data', 
				enableCellEdit: false,
				
				cellTooltip: 
					function( row, col ) {
				      return 'Data: ' + row.entity.date;
				    }, headerTooltip: 
				    function( col ) {
				    	return 'Titulo: ' + col.displayName;
				    }
			},
			{ 		
				name: 'situation',
				field: 'situation', 
				exporterSuppressExport: false,
				displayName: 'Situação', 
				
				enableCellEdit: true,
				enableGridMenu: true,
				enableSorting: true,
				multiSelect: true,
				
				cellFilter: 'situationFilter',
				editDropdownValueLabel: 'situation',
				editableCellTemplate: 'ui-grid/dropdownEditor',				
				
				editDropdownOptionsArray: [						
				    { 
				    	id: 0, situation: 'Aberto' 
				    },
				    {
				    	id: 1, situation: 'Concluído' 
				    },
				    { 
				    	id: 2, situation: 'Aprovado' 
				    },
				    { 
				    	id: 3, situation: 'Pago' 
				    }
				],
				cellTooltip: 
					
					function( row, col ) {		
				    	return 'Situação: ' + row.entity.situation;
				    }, headerTooltip: 
				    function( col ) {
				    	return 'Titulo: ' + col.displayName;
				    }
			},		
			{ 
				field: 'costecenterOid', 
				exporterSuppressExport: false,
				displayName: 'Centro de Custo', 
				enableCellEdit: false,
				
				cellTooltip: 
				function( row, col ) {
			      return 'Centro de custo: ' + row.entity.costecenterOid;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			},
			{ 
				field: 'reason', 
				exporterSuppressExport: false,
				displayName: 'Objetivo da empresa', 
				enableCellEdit: false,	
				
				cellTooltip: 
				function( row, col ) {					
			      return 'Objetivo da empresa: ' + row.entity.reason;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			},			
			{ 
				field: 'typeOid', 
				exporterSuppressExport: true,
				displayName: 'Tipo despesa', 
				enableCellEdit: false,				
				cellTooltip: 
					
				function( row, col ) {
					return 'Tipo de despesa: ' + row.entity.typeOid;
			    }, headerTooltip: 
			    function( col ) {
			    	return 'Titulo: ' + col.displayName;
			    }
			}
		],
		
		exporterFieldCallback: function( grid, row, col, input ) { // FORMATED COLUM PDF AND CSV 
			if( col.name == 'situation' ){
			    switch( input ){
			      case "0":
			        return 'Aberto';
			        break;
			      case "1":
			        return 'Conluido';
			        break;
			      case "2":
			        return 'Aprovado';
			        break;
			      case "3":
			        return 'Pago';
			        break;
			      default:
			        return 'unknown';
			        break;
			    }
			} else {
				return input;
			}
		},		
		
		enableGridMenu: false,
	    enableSelectAll: true,
	    exporterCsvFilename: 'despesas.csv', // nome do arquivo para salvar
       
        exporterPdfHeader: {
         	//title: 'test',
      
        },        
       
        exporterPdfFooter: function ( currentPage, pageCount ) {
		return { 
			text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' 
			};
		},
		exporterPdfDefaultStyle: {
			fontSize: 10
		},
        exporterPdfTableStyle: { // style grid
        	margin: [-40, 0, 0, 0],
		//ESQUEDA, ALTURA, direita, bottom
		},

	    exporterPdfCustomFormatter: function ( docDefinition ) { // style footer and header
          docDefinition.styles.headerStyle = { 
                fontSize: 18, 
                colSpan: 2,
                bold: true,
                margin: [0, 0, 0, 0],
                alignment: 'center'
              
          }; 
          docDefinition.styles.footerStyle = { 
                fontSize: 10, 
                bold: true,
                margin: [0, 0, 0, 0]
          }; 
          return docDefinition;
        },
	    
	    exporterPdfOrientation: 'portrait',
	    exporterPdfPageSize: 'LETTER',
	    exporterPdfMaxGridWidth: 557,
	    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
	    
	    onRegisterApi: function(gridApi){
	        $scope.gridApi = gridApi;
	      }
	};
	
	/* --- Server --- */        // Service
	$scope.load = function() { // load all		
		ExpenseService.query(function( expense ) {
						
			$scope.expense = expense;
			$scope.gridOptions.data = expense;		 		
		}, function( error ) {			 
			toastr.error( error );
		});
	};	
	
	//-- end load 
	$scope.editExpense = function ( e, t ) {			
		var row = $scope.rows[0].entity;
		$scope.update( row );
	};
	
	$scope.update = function ( row ) {				
		ExpenseService.update ( row, function (response ) {				
			toastr.success( response.message );
			$scope.load();
		}, function ( err ){
			toastr.error( 'Erro ' + err.data);
		})
	};
	
	// ---- ROWS checkeds or unchecked	
	$scope.gridOptions.onRegisterApi = function ( gridApi ) {	
		
		$scope.gridApi = gridApi;
		$scope.gridApi.grid.registerRowsProcessor( $scope.singleFilter, 200 );			

		gridApi.selection.on.rowSelectionChanged( $scope, function( row ) {
			
			var msg = row.isSelected; //one selected true or false
		
			if ( msg ) {
				$scope.rows.push( row );
			} else {				
				$scope.rows.splice( $scope.rows.indexOf ( row ) );
			}
		}); // Grid Edit Situation selected
	    gridApi.rowEdit.on.saveRow($scope, function(rowEntity){
	    	$scope.update( rowEntity );
	    });
	};	
	
	$scope.filter = function() {
	    $scope.gridApi.grid.refresh();
	};
	
    $scope.singleFilter = function( renderableRows ) {    	
    	var matcher = new RegExp( $scope.filterValue );     	
    	renderableRows.forEach( function( row ) {
    		
    		var match = false;
    		
    		[ 'code', 'situation', 'date', 'costecenterOid', 'userOid', 'costecenterOid', 'typeOid' ].forEach(function( field ) {    			
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
    	ExpenseService.remove({ids: t}, function(response) {  
    		
			toastr.success("Excluido com sucesso!");
			$scope.load();
    	}), function(err) {    		
    		toastr.error('Erro ' + err);
    	}
    };       
    
    // PDF

	$scope.downloadPDFCSV = function(){  

		$scope.arrayDate=[];
		$scope.Arraytypeg=[];
		$scope.arraydescription=[];
		$scope.arrayvalue=[];
		$scope.arrayendar=[]

		for(var i = 0; i < $scope.expense.length; i ++){
			
			$scope.arrayDate.push($scope.expense[i].date);
			$scope.Arraytypeg.push($scope.expense[i].typeOid);
			$scope.arraydescription.push($scope.expense[i].description);
			$scope.arrayvalue.push("R$ " + $scope.expense[i].value);
		
		};
		$scope.arrayendar.push($scope.arrayDate, $scope.Arraytypeg, $scope.arraydescription, $scope.arrayvalue );
		
		$scope.header = [];
		$scope.employee = [];
		$scope.valueat = [];
		$scope.obds = [];
		$scope.obem = [];
		$scope.columnpdf = [];
		$scope.columpdf = [];
		// Montando Header
		$scope.header.push(			
		    {
		        width: 155,  
		        image: 'data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABkAAD/4QMraHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzEzOCA3OS4xNTk4MjQsIDIwMTYvMDkvMTQtMDE6MDk6MDEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjRGNUQ1NEE3QjQyQjExRTc4MjA0QTg2NzJEMTJCNTNGIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjRGNUQ1NEE2QjQyQjExRTc4MjA0QTg2NzJEMTJCNTNGIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QzYyOUE0QjJCNDA4MTFFN0FFMEU4QTc4OEIwQ0YwQjMiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QzYyOUE0QjNCNDA4MTFFN0FFMEU4QTc4OEIwQ0YwQjMiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/7gAOQWRvYmUAZMAAAAAB/9sAhAABAQEBAQEBAQEBAQEBAQEBAQEB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hv3JoqiIIiCIgiIIln9Hj+aIrBEuPjBIpoqiIIiCIgilgA9/z/REWskhTIq0JZd2ntiKx4poqiIIiCJSJxFQYJoqiIIiCJSJxFQYInx8IJBNFURBEQRLLWfw4RFY4QTRVEQREERBEQREESz0n8OMRWGME0VREERBEs+7X2RFYcUEy4+z4CCQjsTRVEQREERBEstJfDjEVjjFNFURBEh0n+6n8nwnEWoY9ieKtKIIll83d9MRWPemiqJZH7XsERWI4KHP4e36IRVyqPKO78sFIlNFURBEQRKBKIqTFNFURBEsz9n2iIrAcU0VREERBEQREERBEQREERBEQREERBEQREERBEvKPGJBasxTRVpRBEQREESy0l2e2IrHfvTRVEQREERBEQRLL6PD88RWKaKoiCIgiIIiCJQJRFSYpoqiIIiCIgiIIln83f8AREVh3poqiIIiCIgiWXHxiKxTRVEQREERBEQREERBEQRLM/Z9oiKwHFNFUSy0l8OMRWOMU0VREERBEsu7T2xFY8VAEDgfnB+iCuJ2p4q0ogiIIiCJZ9+ntiKw4In9Hj+aCQTRVEpE4ioME0VREERBEs/m7/oiKw700VREERBEs9J/DjEVhjBNFURBEQREERBEsj9r2CIrEcE0VREERBEQREERBEQREESz7tfZEVhxTRVEQREERBEQREERBEQREERBEQREERBEQREERBEQREERBEQREERBEs/n7vpiKw7k0VREERBEQREERBEsvm7vpiKx700VRLM/Z9oiKwHFNFURBEQRLM/Z9oiKwHFNFURBEQREERBEQREEUuSvH5/piYrXFq//2Q==',
		    }, 
	        {
	            colSpan: 3,
	            text: 'Wizco Sistema LTDA', 
	            style: 'tableHeader', 
	            alignment: 'center',
				fontSize: 18, 
				bold: true
	            
	        }, {}, {}					
		);
		$scope.employee.push(
			{
		       text: 'Funcionário:',
		       style: 'tableHeader', 
		            alignment: 'center',
				fontSize: 10, 
				bold: true
		    },
		    {
		        style: 'layoutTable',
		        fontSize: 10, 
		        colSpan: 3,
		        text: ''
		    }, {},{}
		);
		$scope.valueat.push(
		    {
		       text: 'Valor Adiantamento:',
		       style: 'tableHeader', 
		            alignment: 'center',
				fontSize: 10, 
				bold: true
		    },
		    {
		        style: 'layoutTable',
		        fontSize: 10, 
		        colSpan: 3,
		        text: 'R$'
		    }, {},{}
	 		    	
		);
		
		$scope.obds.push(
				{
	 		       text: 'Centro de custo:',
	 		       style: 'tableHeader', 
			            alignment: 'center',
	 				fontSize: 10, 
	 				bold: true
	 		    },
	 		    {
	 		        style: 'layoutTable',
	 		        fontSize: 10, 
	 		        colSpan: 3,
	 		        text: ''
	 		    }, {},{}
		);
		$scope.obem.push(				
			    {
			        text: 'Objetivo da Despesa:',
			        style: 'tableHeader', 
		            alignment: 'center',
					fontSize: 10, 
					bold: true
			    },
			    {
			        style: 'layoutTable',
			        fontSize: 10, 
			        colSpan: 3,
			        alignment: 'center',
			        text: 'Pagamento do certificado digital SSL.'
			    }, {},{}				
		);
		$scope.columnpdf.push(
			{
		        colSpan: 4,
		        text: ' ',
		    },
		    {}, {},{}
		);
		$scope.columpdf.push(
			{
			    text: 'Data', 
			    style: 'tableHeader', 
			    alignment: 'center'										    
			}, 
			{
			    text: 'Gênero', 
			    style: 'tableHeader', 
			    alignment: 'center'										    
			}, 
			{
			    text: 'Descrição', 
			    style: 'tableHeader', 
			    alignment: 'center'
			},
			{										    
			    text: 'Valor', 
			    style: 'tableHeader', 
			    alignment: 'center'										
			}
		);
		// Subtitulo	   
		$scope.docDefinition = { 
				content: [					
					{
						style: 'tableGrid',
						color: '#444',
						table: {
							widths: [155, 199, 100, 100],
							headerRows: 2,
							// keepWithHeaderRows: 1,
							body: [									
								$scope.header,
								$scope.employee,
								$scope.valueat,
								$scope.obds,   						 		
								$scope.obem,	
								$scope.columnpdf,	
								$scope.columpdf,	
								$scope.arrayendar,
							]
						}
					},
				],
				styles: {
					header: {
						fontSize: 18,
						bold: true,
						margin: [0, 0, 0, 10]
					},
					subheader: {
						fontSize: 16,
						bold: true,
						margin: [0, 10, 0, 5]
					},
					tableGrid: {
						margin: [-38, -38, 0, 15]
					},
					tableHeader: {
						bold: true,
						fontSize: 10,
						color: 'black'
					},
					layoutTable: {
					    color: 'black'
					}
				},
				defaultStyle: {
					// alignment: 'justify'
				}
		};
		pdfMake.createPdf($scope.docDefinition, $scope.gridOptions.ALL).open();
		//$scope.gridApi.exporter.pdfExport(uiGridExporterConstants.VISIBLE, $scope.gridOptions.ALL);
	}    
    
	$scope.load();
}])
.filter('situationFilter', function() {	
	var genderHash = {				
		0: 'Aberto',
		1: 'Concluído',
		2: 'Aprovado',
		3: 'Pago'
	};	
	return function(input) {		
		if (!input) { 			
			return '';
		} else {			
			return genderHash[input];
		}
	};
});



