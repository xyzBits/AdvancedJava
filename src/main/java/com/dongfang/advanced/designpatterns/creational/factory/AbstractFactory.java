package com.dongfang.advanced.designpatterns.creational.factory;

/**
 * 相比于工厂方法，抽象工厂的方法中，有多个方法，返回创建的对象
 * 抽象工厂主要分为4类：
 * AbstractFactory:抽象工厂角色，它声明了一组用于创建一种产品的方法，每一个方法对应一种产品，如上述类图中的AbstractFactory就定义了两个方法，分别创建产品A和产品B
 * ConcreteFactory:具体工厂角色，它实现了在抽象工厂中定义的创建产品的方法，生产一组具体产品，这饿产品构件成了一个产品种类，每一个产品都位于某个产品等级结构中，如上述类图中的ConcreteFactoryA和ConcreteFactoryB
 * AbstractProduct：抽象产品角色，为每种产品声明接口，如图中AbstractProductA、AbstractProductB
 * ConcreteProduct：具体产品角色，定义了工厂生产的具体产品对象，实现抽象产品接口声明的业务方法，如图中ConcreteProductA1、ConcreteProductA2、ConcreteProductB1、ConcreteProductB2
 *
 *
 * 链接：https://www.jianshu.com/p/610a26d9d958
 */
public class AbstractFactory {
//    模式代码

    //    AbstractProduct:
    public abstract class Video {
        public abstract void produce();
    }

    //    ConcreteProduct:
    public class PythonVideo extends Video {
        @Override
        public void produce() {
            System.out.println("Python课程视频");
        }
    }

    public class JavaVideo extends Video {
        @Override
        public void produce() {
            System.out.println("录制Java课程视频");
        }
    }

    public class Article {

    }
    public class PythonArticle extends Article{}
    public class JavaArticle extends Article{}

    //    AbstractFactory:
    public interface CourseFactory {
        Video getVideo();

        Article getArticle();
    }

    //    ConcreteFactory:
    public class JavaCourseFactory implements CourseFactory {
        @Override
        public Video getVideo() {
            return new JavaVideo();
        }

        @Override
        public Article getArticle() {
            return new JavaArticle();
        }
    }

    public class PythonCourseFactory implements CourseFactory {
        @Override
        public Video getVideo() {
            return new PythonVideo();
        }

        @Override
        public Article getArticle() {
            return new PythonArticle();
        }
    }

    /**
     * 源码中的使用：
     *
     * java.sql下的Statement、CallableStatement（抽象产品）：
     * public interface Statement extends Wrapper, AutoCloseable {
     *     '省略代码'
     * }
     * public interface CallableStatement extends PreparedStatement {
     *     '省略代码'
     * }
     * com.mysql.jdb下的StatementImpl、CallableStatement（具体产品）
     * public class StatementImpl implements Statement {
     *    '省略代码'
     * }
     * package com.mysql.jdbc;
     * public class CallableStatement extends PreparedStatement implements java.sql.CallableStatement {
     *    '省略代码'
     * }
     * java.sql下的Connection（抽象工厂）：
     * public interface Connection  extends Wrapper, AutoCloseable {
     *
     *     Statement createStatement() throws SQLException;
     *
     *     CallableStatement prepareCall(String sql) throws SQLException;
     *     '省略代码'
     * com.mysql.jdbc下的ConnectionImpl（具体工厂）：
     *
     * public class ConnectionImpl extends ConnectionPropertiesImpl implements Connection {
     *     public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
     *         this.checkClosed();
     *         StatementImpl stmt = new StatementImpl(this, this.database);
     *         stmt.setResultSetType(resultSetType);
     *         stmt.setResultSetConcurrency(resultSetConcurrency);
     *         return stmt;
     *     }
     *    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
     *         if (this.versionMeetsMinimum(5, 0, 0)) {
     *             CallableStatement cStmt = null;
     *             if (!this.getCacheCallableStatements()) {
     *                 cStmt = this.parseCallableStatement(sql);
     *             } else {
     *                 LRUCache var5 = this.parsedCallableStatementCache;
     *                 synchronized(this.parsedCallableStatementCache) {
     *                     ConnectionImpl.CompoundCacheKey key = new ConnectionImpl.CompoundCacheKey(this.getCatalog(), sql);
     *                     CallableStatementParamInfo cachedParamInfo = (CallableStatementParamInfo)this.parsedCallableStatementCache.get(key);
     *                     if (cachedParamInfo != null) {
     *                         cStmt = CallableStatement.getInstance(this, cachedParamInfo);
     *                     } else {
     *                         cStmt = this.parseCallableStatement(sql);
     *                         cachedParamInfo = cStmt.paramInfo;
     *                         this.parsedCallableStatementCache.put(key, cachedParamInfo);
     *                     }
     *                 }
     *             }
     *
     *             cStmt.setResultSetType(resultSetType);
     *             cStmt.setResultSetConcurrency(resultSetConcurrency);
     *             return cStmt;
     *         } else {
     *             throw SQLError.createSQLException("Callable statements not supported.", "S1C00");
     *         }
     *     }
     * }
     *
     * 作者：二妹是只猫
     * 链接：https://www.jianshu.com/p/610a26d9d958
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

}
