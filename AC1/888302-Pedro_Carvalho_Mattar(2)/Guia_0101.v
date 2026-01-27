/*
Guia_0101.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0101;
// define data
integer x;
reg [15:0] b;

// actions
initial
begin : main
    $display ( "Guia_0101 - Tests" );

    // a) 29(10)
    x = 29;
    b = x;
    $display ("a) %d(10) = %16b(2)", x, b);

    // b) 53(10)
    x = 53;
    b = x;
    $display ("b) %d(10) = %16b(2)", x, b);

    // c) 751(10)
    x = 751;
    b = x;
    $display ("c) %d(10) = %16b(2)", x, b);

    // d) 312(10)
    x = 312;
    b = x;
    $display ("d) %d(10) = %16b(2)", x, b);

    // e) 365(10)
    x = 365;
    b = x;
    $display ("e) %d(10) = %16b(2)", x, b);
end // main
endmodule // Guia_0101
