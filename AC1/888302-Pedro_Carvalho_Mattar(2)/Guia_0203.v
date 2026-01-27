/*
Guia_0203.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0203;

// actions
initial
begin : main
    reg [5:0] bin_input;
    reg [7:0] hex_output;
    reg [7:0] oct_output;
    reg [7:0] binary_full;
    reg [3:0] int_part;
    reg [3:0] frac_part;
    
    $display("Guia_0203 - Tests");
    
    // a.) 0,010110(2) = X(4)
    bin_input = 6'b010110;
    $display("a.) 0,010110(2) = 0,%o%o%o(4)", 
             bin_input[5:4], bin_input[3:2], bin_input[1:0]);
    
    // b.) 0,100111(2) = X(8)
    bin_input = 6'b100111;
    $display("b.) 0,100111(2) = 0,%o%o(8)", 
             bin_input[5:3], bin_input[2:0]);
    
    // c.) 0,101001(2) = X(16)
    bin_input = 6'b101001;
    $display("c.) 0,101001(2) = 0,%x%x(16)", 
             bin_input[5:2], {bin_input[1:0], 2'b00});
    
    // d.) 1,110101(2) = X(8)
    bin_input = 6'b110101;
    $display("d.) 1,110101(2) = 1,%o%o(8)", 
             bin_input[5:3], bin_input[2:0]);
    
    // e.) 1011,1011(2) = X(16)
    binary_full = 8'b10111011;
    int_part = binary_full[7:4];
    frac_part = binary_full[3:0];
    $display("e.) 1011,1011(2) = %x,%x(16)", int_part, frac_part);
    
end // main

endmodule // Guia_0203