/*
Guia_0201.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0201;
// define data
real x;
real power2;
integer y;
integer inteiro;
reg [7:0] b;

// actions
initial begin : main
    $display("Guia_0201 - Tests");

    // a) 0,00111(2)
    inteiro = 0;
    b = 8'b00111000;
    x = inteiro;
    power2 = 1.0;
    y = 7;
    while (y >= 0) begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            x = x + power2;
        y = y - 1;
    end
    $display("a) 0,00111(2) = %f", x);

    // b) 0,01001(2)
    inteiro = 0;
    b = 8'b01001000;
    x = inteiro;
    power2 = 1.0;
    y = 7;
    while (y >= 0) begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            x = x + power2;
        y = y - 1;
    end
    $display("b) 0,01001(2) = %f", x);

    // c) 0,10101(2)
    inteiro = 0;
    b = 8'b10101000;
    x = inteiro;
    power2 = 1.0;
    y = 7;
    while (y >= 0) begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            x = x + power2;
        y = y - 1;
    end
    $display("c) 0,10101(2) = %f", x);

    // d) 1,11101(2)
    inteiro = 1;
    b = 8'b11101000;
    x = inteiro;
    power2 = 1.0;
    y = 7;
    while (y >= 0) begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            x = x + power2;
        y = y - 1;
    end
    $display("d) 1,11101(2) = %f", x);

    // e) 11,11001(2)
    inteiro = 3;
    b = 8'b11001000;
    x = inteiro;
    power2 = 1.0;
    y = 7;
    while (y >= 0) begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            x = x + power2;
        y = y - 1;
    end
    $display("e) 11,11001(2) = %f", x);

end
endmodule // Guia_0201
