package data.strategy;

import data.DataFrame;

// Класс реализующий конкретную стратегию, должен реализовывать этот интерфейс
// Класс контекста использует этот интерфейс для вызова конкретной стратегии
interface Strategy {
    Number execute(DataFrame df, String key, Object... others);
}

