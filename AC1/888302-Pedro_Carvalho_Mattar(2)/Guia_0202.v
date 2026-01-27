/*
Guia_0202.v
888302 - Pedro Carvalho Mattar
*/
module Guia_0202;
  
  // Ações 
  initial
  begin : main

    real x;
    integer y;
    reg [7:0] b;
    
    $display("Guia_0202 - Tests");
    
    // a.) 0,875000(10) = X(2)
    x = 0.875000;
    b = 0;
    for (y = 7; y >= 0; y = y - 1) begin
      if (x*2 >= 1) begin
        b[y] = 1;
        x = x*2 - 1;
      end else begin
        b[y] = 0;
        x = x*2;
      end
    end
    $display("a.) 0.875000(10) = 0.%8b(2)", b);
    
    // b.) 1,250000 (10) = X(2)
    x = 0.250000; 
    b = 0;
    for (y = 7; y >= 0; y = y - 1) begin
      if (x*2 >= 1) begin
        b[y] = 1;
        x = x*2 - 1;
      end else begin
        b[y] = 0;
        x = x*2;
      end
    end
    $display("b.) 1.250000(10) = 1.%8b(2)", b);
    
    // c.) 3,750000 (10) = X(2)
    x = 0.750000; 
    b = 0;
    for (y = 7; y >= 0; y = y - 1) begin
      if (x*2 >= 1) begin
        b[y] = 1;
        x = x*2 - 1;
      end else begin
        b[y] = 0;
        x = x*2;
      end
    end
    $display("c.) 3.750000(10) = 11.%8b(2)", b);
    
    // d.) 4,125000 (10) = X(2)
    x = 0.125000; 
    b = 0;
    for (y = 7; y >= 0; y = y - 1) begin
      if (x*2 >= 1) begin
        b[y] = 1;
        x = x*2 - 1;
      end else begin
        b[y] = 0;
        x = x*2;
      end
    end
    $display("d.) 4.125000(10) = 100.%8b(2)", b);
    
    // e.) 7,625000 (10) = X(2)
    x = 0.625000; 
    b = 0;
    for (y = 7; y >= 0; y = y - 1) begin
      if (x*2 >= 1) begin
        b[y] = 1;
        x = x*2 - 1;
      end else begin
        b[y] = 0;
        x = x*2;
      end
    end
    $display("e.) 7.625000(10) = 111.%8b(2)", b);
  end // main
  
endmodule // Guia_0202