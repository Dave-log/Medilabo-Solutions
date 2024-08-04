import React, { createContext, useState, useEffect, useContext } from 'react';

interface AuthContextType {
    isAuthenticated: boolean;
    setIsAuthenticated: (isAuth: boolean) => void;
    handleLogout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// personal Hook to use authentication context
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {
        // check if a token is already in localStorage at loading
        const token = localStorage.getItem('token');
        return !!token;
    });

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsAuthenticated(false);
    };
    
    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsAuthenticated(!!token);
    }, []);
    
    return (
        <AuthContext.Provider value={{ 
            isAuthenticated, 
            setIsAuthenticated: (isAuth: boolean) => setIsAuthenticated(isAuth), 
            handleLogout 
        }}>
            {children}
        </AuthContext.Provider>
    );
}

