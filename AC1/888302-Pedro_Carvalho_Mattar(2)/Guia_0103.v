/*
Guia_0103.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0103;
// define data
integer x;

// actions
initial
begin : main
    $display ( "Guia_0103 - Tests" );

    // a) 45(10) = X(4)
    x = 45;
    $display ("a) %0d(10) = 231(4)", x);

    // b) 66(10) = X(8)
    x = 66;
    $display ("b) %0d(10) = %0o(8)", x, x);

    // c) 79(10) = X(16)
    x = 79;
    $display ("c) %0d(10) = %0x(16)", x, x);

    // d) 151(10) = X(16)
    x = 151;
    $display ("d) %0d(10) = %0x(16)", x, x);

    // e) 781(10) = X(16)
    x = 781;
    $display ("e) %0d(10) = %0x(16)", x, x);
end // main
endmodule // Guia_0103
