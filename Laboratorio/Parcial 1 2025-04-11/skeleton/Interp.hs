module Interp where
import Dibujo
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

type ImagenFlotante = Vector -> Vector -> Vector -> Picture

formaF :: Color -> ImagenFlotante
formaF col d w h = color col (line . map (d V.+) $
    [ vCero, uX, p13, p33, p33 V.+ uY , p13 V.+ uY
    , uX V.+ 4 V.* uY ,uX V.+ 5 V.* uY, x4 V.+ y5
    , x4 V.+ 6 V.* uY, 6 V.* uY, vCero])
  where p33 = 3 V.* (uX V.+ uY)
        p13 = uX V.+ 3 V.* uY
        x4 = 4 V.* uX
        y5 = 5 V.* uY
        uX = (1/6) V.* w
        uY = (1/6) V.* h
        vCero = (0,0)


interp_rotar :: ImagenFlotante -> ImagenFlotante
interp_rotar imagen d w h = imagen (d V.+ w) h (V.negate w)


interp_apilar :: Int -> Int -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_apilar n m imagen1 imagen2 x w h =
    let r' = fromIntegral n / fromIntegral (n + m)
        r = fromIntegral m / fromIntegral (n + m)
        h' = r' V.* h
    in pictures [imagen1 (x V.+ h') w (r V.* h), imagen2 x w h']


interp_encimar :: ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_encimar imagen1 imagen2 x w h = pictures [imagen1 x w h, imagen2 x w h]


--COMPLETAR (EJERCICIO 2-a)
interp_basica :: Bool -> ImagenFlotante
interp_basica b | b == True = formaF blue
                | otherwise = formaF red

--interpretacion de resize, ejercicio 3-a
interp_resize :: Int -> ImagenFlotante -> ImagenFlotante
interp_resize x im d w h = 
 let x' = fromIntegral x
    in im (x' V.* d) (x' V.* w) (x' V.* h)
--COMPLETAR (EJERCicO 2-b)
interp :: Dibujo Bool -> ImagenFlotante
interp dib = foldDib interp_basica interp_rotar interp_apilar interp_encimar interp_resize dib
