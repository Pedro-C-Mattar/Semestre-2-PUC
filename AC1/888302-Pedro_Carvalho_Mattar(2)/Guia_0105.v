/*
Guia_0105.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0105;

reg [7:0] pucmg [0:5];       
reg [7:0] ano [0:6];         
reg [7:0] cidade [0:13];     
reg [7:0] octal [0:4];       
reg [7:0] hexa [0:4];       

initial begin

  pucmg[0] = "P"; pucmg[1] = "U"; pucmg[2] = "C"; 
  pucmg[3] = "-"; pucmg[4] = "M"; pucmg[5] = "G";
  
  ano[0] = "2"; ano[1] = "0"; ano[2] = "2"; ano[3] = "5";
  ano[4] = "-"; ano[5] = "0"; ano[6] = "2";
  
  cidade[0] = "B"; cidade[1] = "e"; cidade[2] = "l"; cidade[3] = "o";
  cidade[4] = " "; cidade[5] = "H"; cidade[6] = "o"; cidade[7] = "r";
  cidade[8] = "i"; cidade[9] = "z"; cidade[10] = "o"; cidade[11] = "n";
  cidade[12] = "t"; cidade[13] = "e";
  
  octal[0] = 8'o124; octal[1] = 8'o141; octal[2] = 8'o162; 
  octal[3] = 8'o144; octal[4] = 8'o145;
  
  hexa[0] = 8'h4E; hexa[1] = 8'h6F; hexa[2] = 8'h69; 
  hexa[3] = 8'h74; hexa[4] = 8'h65;

  $display("Guia_0105 - Tests");
  
  // a.) “PUC-MG” = X(16_ASCII)
  $display("a.) \"PUC-MG\" = %h %h %h %h %h %h", pucmg[0], pucmg[1], pucmg[2], pucmg[3], pucmg[4], pucmg[5]);
  
  // b.) “2025-02” = X(16_ASCII)
  $display("b.) \"2025-02\" = %h %h %h %h %h %h %h", ano[0], ano[1], ano[2], ano[3], ano[4], ano[5], ano[6]);
  
  // c.) “Belo Horizonte" = X(2_ASCII)
  $display("c.) \"Belo Horizonte\" = ");
  $display("%b %b %b %b %b %b %b %b", cidade[0], cidade[1], cidade[2], cidade[3], cidade[4], cidade[5], cidade[6], cidade[7]);
  $display("%b %b %b %b %b %b", cidade[8], cidade[9], cidade[10], cidade[11], cidade[12], cidade[13]);
  
  // d.) 124 141 162 144 145 (8) = X(ASCII)
  $display("d.) 124 141 162 144 145 (8) = %s%s%s%s%s", octal[0], octal[1], octal[2], octal[3], octal[4]);
  
  // e.) 4E 6F 69 74 65 (16) = X(ASCII)
  $display("e.) 4E 6F 69 74 65 (16) = %s%s%s%s%s", hexa[0], hexa[1], hexa[2], hexa[3], hexa[4]);
  
end
endmodule