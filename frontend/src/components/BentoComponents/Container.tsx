import type { ReactNode } from "react";

interface ContainerProps {
  children: ReactNode;
  className?: string;
}

export default function Container({ children, className }: ContainerProps) {
  return <div className={`backdrop-contrast-90 backdrop-blur-md p-4 shadow-2xl   ${className}`}>{children}</div>;
}
