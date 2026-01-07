class MyMath {
   public static int GCF(int n, int m) {
      if (n != 0 && m != 0) {
         if (n < m) {
            int t = n;
            n = m;
            m = t;
         }

         int rem = remainder(n, m);
         return rem == 0 ? m : GCF(m, rem);
      } else {
         return 0;
      }
   }

   public static int LCM(int n, int m) {
      int k = GCF(n, m);
      return k > 0 ? n * m / k : 0;
   }

   public static int remainder(int n, int m) {
      if (n < m) {
         int t = n;
         n = m;
         m = t;
      }

      return n - n / m * m;
   }
}
