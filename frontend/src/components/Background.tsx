import Silk from "./Silk";

export default function Background() {
  return (
    <div className="fixed top-0 left-0 w-full h-full bg-cover bg-center -z-10 bg-no-repeat ">
      <Silk speed={2} scale={1} color="#7B7481" noiseIntensity={1.5} rotation={0} />
    </div>
  );
}
