/*
Guia_0205.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0205_fmt_display;
integer a, b, c;
integer int_part;
reg [2:0] frac_part; 

initial begin
  $display("Guia_0205 - Tests");

  // a) 101,011 + 10,01
  a = 43; b = 18; c = a + b;
  int_part = c/8; frac_part = c%8;
  $display("a) 101,011 + 10,01  = %b,%b", int_part, frac_part);

  // b) 1000,01 - 10,011
  a = 66; b = 19; c = a - b;
  int_part = c/8; frac_part = c%8;
  $display("b) 1000,01 - 10,011 = %b,%b", int_part, frac_part);

  // c) 101,110 * 10,011
  a = 46; b = 19; c = (a * b) / 8;
  int_part = c/8; frac_part = c%8;
  $display("c) 101,110 * 10,011 = %b,%b", int_part, frac_part);

  // d) 10110,01 / 11,101
  a = 178; b = 29; c = (a * 8) / b;
  int_part = c/8; frac_part = c%8;
  $display("d) 10110,01 / 11,101= %b,%b", int_part, frac_part);

  // e) 1100101 % 1101
  a = 101; b = 13; c = a % b;
  $display("e) 1100101 %% 1101   = %b", c);
end
endmodule
