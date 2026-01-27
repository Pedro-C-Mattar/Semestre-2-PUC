/* 
Guia_0104.v 
888302 - Pedro Carvalho Mattar 
*/
module Guia_0104;
  
  // define data
  reg [5:0] a = 6'b10100;   
  reg [5:0] b = 6'b11001;   
  reg [5:0] c = 6'b100101;  
  reg [5:0] d = 6'b101001;  
  reg [5:0] e = 6'b100101;  
  
  // actions
  initial begin : main
    $display("Guia_0104 - Tests");
    
    // a.) 10100(2) = X(4)
    $display("a.) 10100(2) = %d%d%d(4)", a[5:4], a[3:2], a[1:0]); 
    
    // b.) 11001(2) = X(8)
    $display("b.) 11001(2) = %o(8)", b); 
    
    // c.) 100101(2) = X(16)
    $display("c.) 100101(2) = %h(16)", c); 
    
    // d.) 101001(2) = X(8)
    $display("d.) 101001(2) = %o(8)", d); 
    
    // e.) 100101(2) = X(4)
    $display("e.) 100101(2) = %d%d%d(4)", e[5:4], e[3:2], e[1:0]); 

  end // main
endmodule // Guia_0104