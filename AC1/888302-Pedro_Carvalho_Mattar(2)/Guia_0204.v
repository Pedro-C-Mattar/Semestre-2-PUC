/*
Guia_0204_convert_fix.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0204;

  reg [5:0]  a_bin  = 6'b100111;             
  reg [11:0] b_frac = 12'h4D3;                 
  reg [8:0]  c_bin  = 9'b110_101_100;          
  reg [2:0]  d_int  = 3'b110;                  
  reg [11:0] d_frac = 12'b001_100_011_101;    
  reg [3:0]  e_int  = 4'hA;                   
  reg [11:0] e_frac = 12'hDE5;                 

  initial begin
    $display("Guia_0204 - Conversões (corrigido)\n");

    // a) 0,213(4) -> binário
    $display("a) 0,213(4) = 0,%b (2)", a_bin);

    // b) 0,4D3(16) -> base 4
    $display("b) 0,4D3(16) = 0,%0d%0d%0d%0d%0d%0d (4)",
             b_frac[11:10], b_frac[9:8], b_frac[7:6],
             b_frac[5:4],  b_frac[3:2], b_frac[1:0]);

    // c) 0,654(8) -> binário
    $display("c) 0,654(8) = 0,%b (2)", c_bin);

    // d) 6,1435(8) -> base 4
    $display("d) 6,1435(8) = %0d%0d,%0d%0d%0d%0d%0d%0d (4)",
             2'b01, d_int[1:0],
             d_frac[11:10], d_frac[9:8], d_frac[7:6],
             d_frac[5:4],  d_frac[3:2], d_frac[1:0]);

    // e) A,DE5(16) -> base 4
    $display("e) A,DE5(16) = %0d%0d,%0d%0d%0d%0d%0d%0d (4)",
             e_int[3:2], e_int[1:0],
             e_frac[11:10], e_frac[9:8], e_frac[7:6],
             e_frac[5:4],  e_frac[3:2], e_frac[1:0]);

  end
endmodule
