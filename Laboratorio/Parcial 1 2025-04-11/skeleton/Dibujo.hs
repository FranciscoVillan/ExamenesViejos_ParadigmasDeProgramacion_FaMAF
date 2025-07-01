module Dibujo where

-- Definicion  del lenguaje
data Dibujo a = Basica a 
              | Rotar (Dibujo a)
              | Apilar Int Int (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              | Resize Int (Dibujo a)
              deriving(Show, Eq)


-- Funcion Map (de Basicas) para nuestro sub-lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Basica x) = Basica (f x) 
mapDib f (Rotar d1) = Rotar (mapDib f d1)
mapDib f (Apilar n m d1 d2) = Apilar n m (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = Encimar (mapDib f d1) (mapDib f d2)
mapDib f (Resize x d) = Resize x (mapDib f d)

-- Funcion Fold para nuestro sub-lenguaje.
foldDib :: (a -> b) -> (b -> b) ->
       (Int -> Int -> b -> b -> b) -> 
       (b -> b -> b) ->
       (Int -> b -> b) -> Dibujo a -> b

foldDib sB sR sA sEn sRe d =
    let foldDibRecursiva = foldDib sB sR sA sEn sRe 
    in case d of
        Basica x -> sB x
        Rotar d -> sR $ foldDibRecursiva d
        Apilar m n d1 d2 -> sA m n (foldDibRecursiva d1) (foldDibRecursiva d2)
        Encimar d1 d2 -> sEn (foldDibRecursiva d1) (foldDibRecursiva d2)
        Resize x d -> sRe x $ foldDibRecursiva d


--COMPLETAR (EJERCICIO 1-a)
toBool :: Dibujo (Int,Int) -> Dibujo Bool
toBool (Basica (a,b))  |  mod a b == 0  ||  mod b a == 0 = (Basica True)
                       | otherwise = (Basica False)
toBool (Rotar d) = Rotar (toBool d)          
toBool (Apilar x y d1 d2) = Apilar x y (toBool d1) (toBool d2)
toBool (Encimar d1 d2) = Encimar (toBool d1) (toBool d2)
toBool (Resize x d) = Resize x (toBool d)
--COMPLETAR (EJERCICIO 1-b)
-- funcion auxiliar, similar a mod pero que trabaja con duplas (Int, Int)

modalt :: (Int,Int) -> Bool
modalt (a,b) |  mod a b == 0  ||  mod b a == 0 = (True)
              | otherwise = (False)
              
toBool2 :: Dibujo (Int,Int) -> Dibujo Bool
toBool2 dib = mapDib modalt dib


--COMPLETAR (EJERCIO 1-c)
profundidad:: Dibujo a -> Int
profundidad (Basica a) = 1
profundidad (Rotar d) = 1 + profundidad d
profundidad (Apilar _ _ d1 d2) = 1 + max (profundidad d1) (profundidad d2)
profundidad (Encimar d1 d2) = 1 + max (profundidad d1)  (profundidad d2) 
profundidad (Resize x d) = 1 + profundidad d
--COMPLETAR (EJERCICIO 1-d)
profundidad2:: Dibujo a -> Int
profundidad2 dib = foldDib (\x-> 1) (\x -> x + 1) (\_ _ x y -> 1 + max x y) (\x y -> 1 + max x y) (\_ x -> x + 1) dib  
