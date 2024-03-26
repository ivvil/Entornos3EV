import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Movimiento implements Comparable<Movimiento> {
	private Calendar fecha;
	private Double importe;
	private Boolean ingresa_saca;
	private CuentaCorriente cuenta;
	private Integer numeroMovimiento;
	private static int numSig = 1;

	public Movimiento(Calendar fecha, double importe, boolean ingresa_saca, CuentaCorriente cuenta)
			throws ObjetoErroneo {
		super();
		this.fecha = fecha;
		this.importe = importe;
		this.ingresa_saca = ingresa_saca;
		this.cuenta = cuenta;
		if (fecha == null || importe <= 0 || cuenta == null) {
			throw new ObjetoErroneo();
		}
		numeroMovimiento = numSig;
		numSig++;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public Double getImporte() {
		return importe;
	}

	public Boolean getIngresa_saca() {
		return ingresa_saca;
	}

	public CuentaCorriente getCuenta() {
		return cuenta;
	}

	public Integer getNumeroMovimiento() {
		return numeroMovimiento;
	}

	public static int getNumSig() {
		return numSig;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Movimiento " + numeroMovimiento + ", fecha=" + sdf.format(fecha.getTime())
				+ (ingresa_saca ? "          " : " -") + String.format("%10.2f", importe);
	}

	public String todoMov() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Movimiento " + numeroMovimiento + "  número de cuenta= " + cuenta + ", fecha="
				+ sdf.format(fecha.getTime()) + (ingresa_saca ? "          " : " -") + String.format("%10.2f", importe);
	}

	@Override
	public int compareTo(Movimiento o) {
		// TODO Auto-generated method stub
		if (cuenta.compareTo(o.getCuenta()) == 0) {
			return fecha.compareTo(o.getFecha());
		}
		return cuenta.compareTo(o.getCuenta());
	}

}
