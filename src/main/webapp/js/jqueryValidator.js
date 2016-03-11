$.validator.addMethod(
										"dateTest",
										function(value, element) {
											if(value == null || value == "")
												{return true;}
											var r = new RegExp(strings['regex']);
											return value.match(r);
										},
										"");

						$.validator.addMethod("alphanumeric", function(value,
								element) {
							return this.optional(element) || /^[a-zA-Z0-9-_' ]+$/.test(value);
						}, "Letters, numbers or underscores only please");
						$('#editcomputer')
								.validate(
										{
											lang : "${pageContext.response.locale}",
											rules : {
												name : {
													alphanumeric : true,
													required : true,
													minlength : 3
												},
												introduced : {
													dateTest : true
												},
												discontinued : {
													dateTest : true
												}
											},
											messages : {
												name : strings['name'],
												introduced : strings['intro'],
												discontinued : strings['discon']
											},
											error : function(label) {
												$(this).addClass("div");
											}
										});
						$('#addcomputer')
						.validate(
								{
									lang : "${pageContext.response.locale}",
									rules : {
										name : {
											alphanumeric : true,
											required : true,
											minlength : 3
										},
										introduced : {
											dateTest : true
										},
										discontinued : {
											dateTest : true
										}
									},
									messages : {
										name : strings['name'],
										introduced : strings['intro'],
										discontinued : strings['discon']
									},
									error : function(label) {
										$(this).addClass("div");
									}
								});