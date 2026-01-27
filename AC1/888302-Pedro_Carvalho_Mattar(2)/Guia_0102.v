/*
Guia_0102.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0102;
// define data
integer x = 0;        // decimal
reg [7:0] b = 0;      // até 8 bits

// actions
initial
begin : main
    $display ( "Guia_0102 - Tests" );

    // a) 10001(2)
    b = 8'b00010001;
    x = b;
    $display ("a) %b(2) = %d(10)", b, x);

    // b) 10111(2)
    b = 8'b00010111;
    x = b;
    $display ("b) %b(2) = %d(10)", b, x);

    // c) 10100(2)
    b = 8'b00010100;
    x = b;
    $display ("c) %b(2) = %d(10)", b, x);

    // d) 101010(2)
    b = 8'b00101010;
    x = b;
    $display ("d) %b(2) = %d(10)", b, x);

    // e) 110011(2)
    b = 8'b00110011;
    x = b;
    $display ("e) %b(2) = %d(10)", b, x);
end // main
endmodule // Guia_0102
